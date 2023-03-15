package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class JsonMultipartFile implements MultipartFile {
    private String name;

    private String originalFileName;
    private String contentType;
    private String base64StringContent;
    private long size;

    public JsonMultipartFile() {
    }

    public JsonMultipartFile(String name, String originalFileName, String contentType, String base64StringContent, long size) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.base64StringContent = base64StringContent;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFileName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return (this.base64StringContent == null ||this.base64StringContent.isBlank());
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
       return  Base64.getDecoder().decode(this.base64StringContent);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        //to be implemented
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        //to be implemented

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBase64StringContent(String base64StringContent) {
        this.base64StringContent = base64StringContent;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "JsonMultipartFile{" +
                "name='" + name + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", base64StringContent='" + base64StringContent + '\'' +
                ", size=" + size +
                '}';
    }
}
