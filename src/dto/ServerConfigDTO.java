package dto;

/**
 * Created by Vilkaz on 31.08.2015.
 */
public class ServerConfigDTO {
    private String hostname;
    private int port;

   public ServerConfigDTO(String hostname, int port){
        this.hostname=hostname;
        this.port=port;
    }

    //region getter and setter

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }


    //endregion getter and setter
}
