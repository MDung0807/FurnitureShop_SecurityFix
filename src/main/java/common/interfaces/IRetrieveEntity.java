package common.interfaces;

import javax.servlet.ServletException;
import java.util.ArrayList;

public interface IRetrieveEntity <ReturnType, EntityType, IdType> {
    ReturnType retrieveById(IdType entityId) throws ServletException;
    ArrayList<ReturnType> retrieveAll(EntityType request) throws ServletException;
}
