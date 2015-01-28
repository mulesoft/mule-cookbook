package org.mule.tools.devkit.store.client;

public class MuleStoreClient{
    public MuleStoreClient();

    public MuleStoreClient(String address);
    
    public List<Entity> getRecentlyAdded() throws SessionExpiredException;

    public Entity get(int id) throws NoSuchEntityException, SessionExpiredException;

    public List<Entity> searchWithQuery(String query,Integer page,Integer pageSize) throws NoSuchEntityException,SessionExpiredException;

    public Entity update(Entity entity) throws NoSuchEntityException, InvalidEntityException,SessionExpiredException;

    public List<Entity> addList( List<Entity> entities) throws InvalidEntityException,SessionExpiredException;

    public List<Entity> getList( List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException;

    public void delete(int id) throws NoSuchEntityException,SessionExpiredException;

    public List<Entity> updateList(List<Entity> entities) throws NoSuchEntityException, InvalidEntityException,SessionExpiredException;

    public void deleteList(List<Integer> entityIds) throws NoSuchEntityException,SessionExpiredException;

    public Entity add(Entity entity) throws InvalidEntityException,SessionExpiredException;

    public CustomEntity describe(String entityId) throws NoSuchEntityException,SessionExpiredException;

    public List<CustomEntity> getCustomEntities() throws SessionExpiredException;

    CustomEntity createCustomEntityDefinition(CustomEntity entityDefinition) throws InvalidEntityException, SessionExpiredException;
}
