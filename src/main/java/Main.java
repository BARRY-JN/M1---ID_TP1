import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Main {

    private final static String wifiPath="Integration_Donnée_TP1_DATA/sites-disposant-du-service-paris-wi-fi.csv";
    private final static String velibPath="Integration_Donnée_TP1_DATA/velib_a_paris_et_communes_limitrophes.csv";
    private final static String tournagePath="Integration_Donnée_TP1_DATA/tournagesdefilmsparis2011.csv";


    public static void main(String[] args) {
        WrapperCSV wrapperCSV = new WrapperCSV();
        Mediator mediator = new Mediator();
        String wifiRequest= wrapperCSV.createRequestFromCSV(wifiPath,"WIFI");
        String velibRequest=wrapperCSV.createRequestFromCSV(velibPath,"VELIB");
        String tournageRequest=wrapperCSV.createRequestFromCSV(tournagePath,"TOURNAGE");
        mediator.addDataSource(wifiRequest);
        mediator.addDataSource(velibRequest);
        mediator.addDataSource(tournageRequest);

       /* String wifiInsert = wrapperCSV.insertInto(wifiPath,"WIFI");
        String velibInsert = wrapperCSV.insertInto(velibPath,"VELIB");
        String tournageInsert = wrapperCSV.insertInto(tournagePath,"TOURNAGE");
        jdbc.executeUpdate(wifiInsert);
        jdbc.executeUpdate(velibInsert);
        jdbc.executeUpdate(tournageInsert);*/
        mediator.update(wrapperCSV.create_view("WIFI"));
        mediator.update(wrapperCSV.create_view("VELIB"));
        mediator.update(wrapperCSV.create_view("TOURNAGE"));
        mediator.query(wrapperCSV.show_table("view"+"VELIB"));
        mediator.requestFromUser("SELECT titre realisateur Nom_du_site FROM mediator"); // L'utilisateur ignore la structure interne de la basen il fait la demande au mediator

    }
    public static void printResultSet(ResultSet res){
        try{
            ResultSetMetaData rsMeta = res.getMetaData();
            int columnCount = rsMeta.getColumnCount();
            while(res.next()){
                System.out.println("\n----------");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsMeta.getColumnName(i);
                    System.out.format("%s:%s\n", columnName, res.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
