package pl.put.poznan.analyzer.commons;

import javax.persistence.*;

@Entity
public class Network {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_Sequence")
    @SequenceGenerator(name = "grade_Sequence", sequenceName = "GRADE_SEQ")
    private int id;

    @Column
    @Lob
    private String networkJson;

    public int getId() {
        return id;
    }


    public String getNetworkJson() {
        return networkJson;
    }

    public void setNetworkJson(String networkJson) {
        this.networkJson = networkJson;
    }

    public Network(String networkJson) {
        this.networkJson = networkJson;
    }
}
