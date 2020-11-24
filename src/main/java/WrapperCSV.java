import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class WrapperCSV {

    String createRequestFromCSV(String path, String tableName){
        try {
            String file= readFile(path);
            String[] files=file.split("\n");
            StringBuilder sqlQuery= new StringBuilder();
            sqlQuery.append("CREATE TABLE if not exists ").append(tableName).append("(").append("\n");
            sqlQuery.append("id INT PRIMARY KEY NOT NULL");
            String[] columnName= files[0].split(";");
            for(String s:columnName ){
                s=s.replace(" ","_").replace("\r","").replace("\n","");
                sqlQuery.append(",\n");
                sqlQuery.append(s).append(" ").append("Varchar(255)");
            }
            sqlQuery.append(");");
            return sqlQuery.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

     String insertInto(String path, String tableName){
        StringBuilder sqlQuery=new StringBuilder();
        try {
            String file = readFile(path);
            String[] files=file.split("\n");
            sqlQuery.append("INSERT INTO ").append(tableName).append(" VALUES ");
            for(int i=1;i<files.length;i++){
                if(i!=1){
                    sqlQuery.append(",\n");
                }
                String[] values= files[i].replace("\r"," ").replace("\n","").
                        replace("'"," ").split(";");
                sqlQuery.append("(").append(i).append(", ");
                boolean first=true;
                for(String value :values){
                    if(!first){
                        sqlQuery.append(", ");
                    }
                    if(value.equals(" "))value="NULL";
                    sqlQuery.append("'").append(value).append("'");
                    first=false;
                }
                sqlQuery.append(")");
            }
            sqlQuery.append(";");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlQuery.toString();
    }

    private String  readFile (  String  path  ) throws IOException {
        byte []  encoded = Files.readAllBytes(Paths.get(path));
        return new  String ( encoded ,  StandardCharsets.UTF_8);
    }
    
    String show_table(String name) {
    	return "SELECT * FROM "+name;
    }
    
    String create_view(String name) {
    	return " CREATE OR REPLACE VIEW view"+name +" AS " +"SELECT * FROM "+name;
    }
}
