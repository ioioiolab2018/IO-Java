package pl.put.poznan.analyzer.commons;

import javax.persistence.*;

@Entity
public class Network {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "network_Sequence")
    @SequenceGenerator(name = "network_Sequence", sequenceName = "NETWORK_SEQ")
    private int id;

    @Column
    @Lob
    private String jsonValue;

    public int getId() {
        return id;
    }


    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String networkJson) {
        this.jsonValue = networkJson;
    }

    public Network(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public Network() {
    }
}
