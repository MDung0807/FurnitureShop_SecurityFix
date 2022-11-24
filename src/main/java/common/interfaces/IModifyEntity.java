package common.interfaces;

public interface IModifyEntity<CreateType, UpdateType, IdType> {
    int insert(CreateType request);
    boolean update(UpdateType request);
    boolean delete(IdType entityId);
}
