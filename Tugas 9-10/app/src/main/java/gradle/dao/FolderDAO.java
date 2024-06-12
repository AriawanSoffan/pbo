package gradle.dao;

import gradle.entities.Folder;
import java.util.*;

public interface FolderDAO {
    public int createFolder(String foldername);

    public ArrayList<Folder> listAllFolders();
}
