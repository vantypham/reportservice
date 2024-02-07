package com.example.dto;

public class SMS {
    private String id;
    private String image;
    private String containerName;
    private String port;

    public SMS(String id, String image, String containerName, String port) {
        this.id = id;
        this.image = image;
        this.containerName = containerName;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", containerName='" + containerName + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
