package usa.edu.mum.wap.model;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 20:14
 */
public class Team {

    private String name;
    private Integer id;

    public Team(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
