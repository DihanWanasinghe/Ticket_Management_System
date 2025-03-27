package Configurations;

import java.io.FileWriter;

import Configurations.Configuration;
import com.google.gson.Gson;
import java.io.*;

/**
 * Focuses on saving the details of a Configurations.Configuration object into a json file and ,
 * retrieving json file content into a Configurations.Configuration object
 * <p>
 * collaborates with the Gson library . Consist static methods only.
 * No need of instance creation because of that
 * </p>
 *
 * <p>
 *     <strong>Use Case</strong>
 *     <pre>
 *       Configurations.Configuration configuration=  Configurations.ConfigurationManager.loadConfiguration("Configurations.json");
 *       Configurations.ConfigurationManager.saveConfiguration(configuration,"Configurations.json");
 *     </pre>
 *
 * </p>
 */
public class ConfigurationManager {
    /**
     * Saves Configurations.Configuration object details into a json file
     * @param configuration Configurations.Configuration object with User Input Details
     * @param filename  Name of the json file we are going to save the details to .
     */
    public static void saveConfiguration(Configuration configuration,String filename){
            FileWriter fileWriter=null;
        try {
             fileWriter = new FileWriter(filename);
            Gson gson = new Gson();
            gson.toJson(configuration,fileWriter);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves details from a saved json file into a Configurations.Configuration object
     * These details are on previous user inputs
     * @param filename Name of the json file we retrieve  the Configurations.Configuration details from
     * @return
     */

    public static Configuration loadConfiguration(String filename){

        FileReader fileReader = null;
        try {
             fileReader = new FileReader(filename);
            Gson gson = new Gson();
            return gson.fromJson(fileReader, Configuration.class);
        }catch (IOException e){
            System.err.println("Sorry , NO previously saved configurations");
        }finally {
            try {
                fileReader.close();
            }catch (IOException e ){
                e.printStackTrace();
            }catch (NullPointerException e){

                System.out.println("");
            }
        }
        return null;

    }


}
