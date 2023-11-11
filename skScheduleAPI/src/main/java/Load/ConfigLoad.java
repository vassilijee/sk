package Load;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigLoad {
    private Integer index;
    private String poHead;
    private String poSpec;

    public ConfigLoad(Integer index, String poHead, String poSpec) {
        this.index = index;
        this.poHead = poHead;
        this.poSpec = poSpec;
    }

    @Override
    public String toString() {
        return "{" + index + "; head_cfg - " + poHead + "; head_spec - " + poSpec + '}';
    }
}
