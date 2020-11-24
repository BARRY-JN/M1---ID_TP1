import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Mediator {
    private final  Jdbc jdbc = new Jdbc();
    static List<List<String>> globalSchema= new ArrayList<>();
    static List<String> tableName=new ArrayList<>();

    public  ResultSet requestFromUser(String query){
        List<String> sourceName=getSourceNeeded(query);
        String request=createRequestUser(query,sourceName);
        ResultSet result = query(request);
        return result;
    }

    /* Créer une requete utilisant les vue necesaires contenue dans sourceNeeded.
       La jointure entre les vue doit se faire sur une approximation de la distance via les point GPS present dans chacune
       des view. A savoir  viewWIFI : geo_point_2d
                       viewTOURNAGE : xy
                          viewVELIB : wgs84
     */
    public String createRequestUser(String originalQuery, List<String> sourceNeeded){
        return "todo";
    }


    public List<String> getSourceNeeded(String query){
        String column= query.substring(query.indexOf("SELECT")+"Select".length());
        column=column.substring(0,column.indexOf("FROM"));
        String[] names=column.split(" ");
        List<String> result=new ArrayList<>();
        for(String name: names){
            if(name.equals("*")){
                result.addAll(tableName);
                break;
            }
            int tableNumber=0;
            for(List<String> table: globalSchema){
                if(table.contains(name) &&!result.contains(tableName.get(tableNumber))){
                    result.add(tableName.get(tableNumber));
                    break;
                }
                tableNumber++;
            }
        }
        return result;
    }

    public  void addToMediatorSchema(String table){
        String name= table.substring(table.indexOf("exists")+"exists".length());
        name=name.substring(0,name.indexOf("("));
        tableName.add(name);
        globalSchema.add(new ArrayList<String>());
        String[] column= table.split("\n");
        System.out.println("");
        for(int i=2;i<column.length;i++){
            String[] columnName= column[i].split(" ");
            globalSchema.get(globalSchema.size()-1).add(columnName[0]);
        }
    }
    public  void addDataSource(String query){
        addToMediatorSchema(query);
        update(query);
    }
    public void update(String query){
        jdbc.executeUpdate(query);
    }
    public ResultSet query(String query){
        return jdbc.executeQuery(query);
    }
}
