package usa.edu.mum.wap.model;

import java.util.List;

/**
 * User: Enkh A Erdenebat
 * Date: 2017-11-20
 * Time: 20:14
 */
public class Team {

    private String name;
    private Integer id;
    private List<TeamMember> teamMemberList;

    public Team(String name, Integer id, List<TeamMember> teamMemberList) {
        this.name = name;
        this.id = id;
        this.teamMemberList = teamMemberList;
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

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }
}
