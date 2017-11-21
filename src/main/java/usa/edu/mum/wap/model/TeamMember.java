package usa.edu.mum.wap.model;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 22:34
 */
public class TeamMember {

    private Integer id;
    private Integer userId;

    public TeamMember(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public TeamMember() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
