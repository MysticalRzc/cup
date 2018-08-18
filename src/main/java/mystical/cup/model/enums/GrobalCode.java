package mystical.cup.model.enums;

/**
 * Created by Rzc on 2018/10/3.
 */
public enum GrobalCode{
    REQUEST_POST_KEY("POST", "Post request")
    , REQUEST_GET_KEY("GET", "Get request");

    private String key;
    private String desc;


    GrobalCode(String key, String desc){
        this.key = key;
        this.desc = desc;
    }

    public String getKey(){
        return key;
    }

    public String getDesc(){
        return desc;
    }
}
