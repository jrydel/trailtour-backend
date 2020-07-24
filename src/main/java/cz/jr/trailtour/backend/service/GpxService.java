package cz.jr.trailtour.backend.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import cz.jr.trailtour.backend.repository.GpxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Jiří Rýdel on 7/6/20, 11:31 AM
 */
@Service
public class GpxService {

    private final GpxRepository gpxRepository;
    private final XmlMapper xmlMapper;

    @Autowired
    public GpxService(GpxRepository gpxRepository, XmlMapper xmlMapper) {
        this.gpxRepository = gpxRepository;
        this.xmlMapper = xmlMapper;
    }

    public void storeFile(String database, MultipartFile file) throws IOException, SQLException {
        if (file.getOriginalFilename() == null) {
            throw new IOException("OriginalFileName is null.");
        }
        gpxRepository.saveGpx(database, StringUtils.cleanPath(file.getOriginalFilename()), file.getBytes());
    }

    public Map<String, Object> getFile(String database, Long id) throws SQLException, JAXBException, XMLStreamException, IOException {
        GpxRepository.GpxEntity gpxEntity = gpxRepository.getGpx(database, id);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.putIfAbsent("name", gpxEntity.getName());
        resultMap.putIfAbsent("content", parseXml(gpxEntity.getContent()));
        return resultMap;
    }

    public Path createTempGpxFile(String database, Path parentPath, Long id) throws SQLException, IOException {
        GpxRepository.GpxEntity gpx = gpxRepository.getGpx(database, id);
        return Files.write(Paths.get(parentPath.toString(), System.currentTimeMillis() + ".gpx"), gpx.getContent());
    }

    public List<Map<String, Object>> getAllFiles(String database) throws SQLException {
        return gpxRepository.getAllGpx(database);
    }

    private Collection<GpxEntry> parseXml(byte[] data) throws XMLStreamException, IOException {
        List<GpxEntry> list = new ArrayList<>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(data));

        while (reader.hasNext()) {
            reader.next();
            if (reader.isStartElement() && reader.getLocalName().equals("trkpt")) {
                GpxEntry gpxEntry = xmlMapper.readValue(reader, GpxEntry.class);
                list.add(gpxEntry);
            }
        }

        return list;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GpxEntry {
        @JsonProperty(value = "lat")
        @JacksonXmlProperty(localName = "lat", isAttribute = true)
        private double latitude;
        @JsonProperty(value = "lon")
        @JacksonXmlProperty(localName = "lon", isAttribute = true)
        private double longitude;
        @JsonProperty(value = "time")
        @JacksonXmlProperty(localName = "time")
        private String timestamp;
        @JsonProperty(value = "ele")
        @JacksonXmlProperty(localName = "ele")
        private double elevation;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public double getElevation() {
            return elevation;
        }

        public void setElevation(double elevation) {
            this.elevation = elevation;
        }
    }
}
