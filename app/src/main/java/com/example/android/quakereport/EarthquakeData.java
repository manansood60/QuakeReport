package com.example.android.quakereport;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.List;

public class EarthquakeData implements Serializable {
    private List<Features> features;

    private Metadata metadata;

    private List<Double> bbox;

    private String type;

    public List<Features> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Double> getBbox() {
        return this.bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Features implements Serializable {
        private Geometry geometry;

        private String id;

        private String type;

        private Properties properties;

        public Geometry getGeometry() {
            return this.geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Properties getProperties() {
            return this.properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }

        public static class Geometry implements Serializable {
            private List<Double> coordinates;

            private String type;

            public List<Double> getCoordinates() {
                return this.coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class Properties implements Serializable {
            private Double dmin;

            private String code;

            private String sources;

            private Object tz;

            private Double mmi;

            private String type;

            private String title;

            private String magType;

            private Integer nst;

            private Integer sig;

            private Integer tsunami;

            private Double mag;

            private String alert;

            private Integer gap;

            private Double rms;

            private String place;

            private String net;

            private String types;

            private Object felt;

            private Object cdi;

            private String url;

            private String ids;

            private Long time;

            private String detail;

            private Long updated;

            private String status;

            public Double getDmin() {
                return this.dmin;
            }

            public void setDmin(Double dmin) {
                this.dmin = dmin;
            }

            public String getCode() {
                return this.code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getSources() {
                return this.sources;
            }

            public void setSources(String sources) {
                this.sources = sources;
            }

            public Object getTz() {
                return this.tz;
            }

            public void setTz(Object tz) {
                this.tz = tz;
            }

            public Double getMmi() {
                return this.mmi;
            }

            public void setMmi(Double mmi) {
                this.mmi = mmi;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMagType() {
                return this.magType;
            }

            public void setMagType(String magType) {
                this.magType = magType;
            }

            public Integer getNst() {
                return this.nst;
            }

            public void setNst(Integer nst) {
                this.nst = nst;
            }

            public Integer getSig() {
                return this.sig;
            }

            public void setSig(Integer sig) {
                this.sig = sig;
            }

            public Integer getTsunami() {
                return this.tsunami;
            }

            public void setTsunami(Integer tsunami) {
                this.tsunami = tsunami;
            }

            public Double getMag() {
                return this.mag;
            }

            public void setMag(Double mag) {
                this.mag = mag;
            }

            public String getAlert() {
                return this.alert;
            }

            public void setAlert(String alert) {
                this.alert = alert;
            }

            public Integer getGap() {
                return this.gap;
            }

            public void setGap(Integer gap) {
                this.gap = gap;
            }

            public Double getRms() {
                return this.rms;
            }

            public void setRms(Double rms) {
                this.rms = rms;
            }

            public String getPlace() {
                return this.place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getNet() {
                return this.net;
            }

            public void setNet(String net) {
                this.net = net;
            }

            public String getTypes() {
                return this.types;
            }

            public void setTypes(String types) {
                this.types = types;
            }

            public Object getFelt() {
                return this.felt;
            }

            public void setFelt(Object felt) {
                this.felt = felt;
            }

            public Object getCdi() {
                return this.cdi;
            }

            public void setCdi(Object cdi) {
                this.cdi = cdi;
            }

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getIds() {
                return this.ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public Long getTime() {
                return this.time;
            }

            public void setTime(Long time) {
                this.time = time;
            }

            public String getDetail() {
                return this.detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public Long getUpdated() {
                return this.updated;
            }

            public void setUpdated(Long updated) {
                this.updated = updated;
            }

            public String getStatus() {
                return this.status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

    public static class Metadata implements Serializable {
        private Long generated;

        private Integer count;

        private String api;

        private String title;

        private String url;

        private Integer status;

        public Long getGenerated() {
            return this.generated;
        }

        public void setGenerated(Long generated) {
            this.generated = generated;
        }

        public Integer getCount() {
            return this.count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getApi() {
            return this.api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
