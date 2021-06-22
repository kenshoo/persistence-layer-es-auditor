package com.kenshoo.pl.es.auditor;

class ElasticSearchConfiguration {

  private final String name;
  private final String password;
  private final String url;
  private final String serviceName;

    private ElasticSearchConfiguration(String name, String password, String url, String serviceName) {
        this.name = name;
        this.password = password;
        this.url = url;
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getServiceName() {
        return serviceName;
    }

    static class Builder{
        private  String name;
        private  String password;
        private  String url;
        private  String serviceName;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public ElasticSearchConfiguration build(){
            return new ElasticSearchConfiguration(name, password, url, serviceName);
        }
    }
}
