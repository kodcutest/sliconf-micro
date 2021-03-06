package javaday.istanbul.sliconf.micro.model.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Sponsor {

    private String id;
    private String logo;
    private String name;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Sponsor) {
            Sponsor sponsor = (Sponsor) obj;

            if (Objects.nonNull(this.getName()) && this.getName().equals(sponsor.getName()) &&
                    Objects.nonNull(this.getLogo()) && this.getLogo().equals(sponsor.getLogo())) {
                return true;
            }
        }

        return false;
    }
}
