package main.java.data.datahandler;

import java.io.File;

import java.io.Serializable;

import net.lingala.zip4j.core.ZipFile;

import main.java.schema.GameBlueprint;


/**
 * DataBundles store two objects. One object is a GameBlueprint,
 * which contains data relevant to the authoring environment.
 * The other object is a GameState, which contains data
 * relevant to the engine. There are public getter and setter methods
 * for both objects so that data can be properly saved and retrieved
 * to/from the data bundle.
 * 
 * @author In-Young Jo
 * 
 */

@SuppressWarnings("serial")

// Right now ZipFiles cannot be serialized, so I'm putting in the data bundle the location of the zipfile until we
// can figure out how to serialize zipfiles w/ the gameblueprints.

public class DataBundle implements Serializable {

    private GameBlueprint myGameBlueprint;
//    private ZipFile myZippedResources;
    private String myZippedResourcesLocation;

    public DataBundle () {
    }

    public DataBundle (GameBlueprint gameBlueprintInit) {
        myGameBlueprint = gameBlueprintInit;
    }

    public GameBlueprint getBlueprint () {
        return myGameBlueprint;
    }
    
//    public ZipFile getZippedResourcesFolder(){
//    	return myZippedResources;
//    }
    
    public String getZippedResourcesFolderLocation(){
    	return myZippedResourcesLocation;
    }
    
//    public void setResourcesFolder(ZipFile resourceFolderToSave){
//    	myZippedResources = resourceFolderToSave;
//    }
    
    public void setResourceFolderLocation(String resourceFolderLocationToSave){
    	myZippedResourcesLocation = resourceFolderLocationToSave;
    }

    public void setBlueprint (GameBlueprint gameBlueprint) {
        myGameBlueprint = gameBlueprint;
    }

}
