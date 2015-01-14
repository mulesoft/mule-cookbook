package org.mule.tools.devkit.store.service;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Mulesoft.
 */
@javax.jws.WebService(
        serviceName = "IMuleStoreServiceService",
        portName = "IMuleStoreServicePort",
        targetNamespace = "http://service.store.devkit.tools.mule.org/",
        wsdlLocation = "wsdl/IMuleStoreService.wsdl",
        endpointInterface = "org.mule.tools.devkit.store.service.IMuleStoreService")
public class MuleStoreServiceImpl implements IMuleStoreService {

    private static final Logger LOG = Logger.getLogger(MuleStoreServiceImpl.class.getName());

    Integer currentIndex=0;
    private Map<Integer,Entity> entities = new HashMap<>();

    private Author getAuthor() throws DatatypeConfigurationException {
        Author author = new Author();
        author.setFirstName("Fulanito");
        author.setFirstName("Cosme");
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        author.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        return author;
    }

    private Book getBook() {
        Book book = new Book();
        book.setAuthorId(1);
        book.setTitle("Iliad");
        book.setDescription(
                "The Iliad (/ˈɪliəd/;[1] sometimes referred to as the Song of Ilion or Song of Ilium) is an ancient Greek epic poem in dactylic hexameter, traditionally attributed to Homer. Set during the Trojan War, the ten-year siege of the city of Troy (Ilium) by a coalition of Greek states, it tells of the battles and events during the weeks of a quarrel between King Agamemnon and the warrior Achilles.");
        book.setGenre(Genre.HISTORY);
        return book;
    }

    @Override public List<Entity> getRecentlyAdded() throws SessionExpiredException{
        LOG.info("Executing operation getRecentlyAdded");
        try {
            List<org.mule.tools.devkit.store.service.Entity> list = new ArrayList<>();
            Book book = getBook();
            list.add(book);
            Author author = getAuthor();
            list.add(author);
            return list;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override public Entity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException{
        if(!entities.containsKey(id)){

            throw new NoSuchEntityException();
        }
        return entities.get(id);
    }

    @Override public List<Entity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
            @WebParam(name = "pageSize", targetNamespace = "") Integer pageSize) throws NoSuchEntityException,SessionExpiredException {

        LOG.info("Executing operation searchWithQuery");
        try {
            List<org.mule.tools.devkit.store.service.Entity> list = new ArrayList<>();
            Book book = getBook();
            list.add(book);
            Author author = getAuthor();
            list.add(author);
            return list;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override public List<CustomEntity> getCustomEntities() throws SessionExpiredException {
        return null;
    }

    @Override public Entity update(@WebParam(name = "entity", targetNamespace = "") Entity entity) throws InvalidEntityException, NoSuchEntityException {
        if(!entities.containsKey(entity.getId())){
            throw new NoSuchEntityException();
        }
        entities.put(entity.getId(),entity);
        return entity;
    }

    @Override public CustomEntity createCustomEntityDefinition(@WebParam(name = "entityDefinition", targetNamespace = "") CustomEntity entityDefinition)
            throws SessionExpiredException, InvalidEntityException {
        return null;
    }

    @Override public List<Entity> addList(@WebParam(name = "entities", targetNamespace = "") List<Entity> entities) throws InvalidEntityException, SessionExpiredException {
        for(Entity entity:entities){
            add(entity);
        }
        return entities;
    }

    @Override public List<Entity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        List<Entity> returnValue = new ArrayList<>();
        for(Integer id:entityIds){
            returnValue.add(get(id));
        }
        return returnValue;
    }

    @Override public void delete(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException, SessionExpiredException {
        if(!entities.containsKey(id)){
            throw new NoSuchEntityException();
        }
        entities.remove(id);
    }

    @Override public List<Entity> updateList(@WebParam(name = "entities", targetNamespace = "") List<Entity> entities) throws InvalidEntityException, NoSuchEntityException, SessionExpiredException {
        for(Entity entity:entities){
            update(entity);
        }
        return entities;
    }

    @Override public void deleteList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException, SessionExpiredException {
        for(Integer id:entityIds){
            delete(id);
        }
    }

    @Override public CustomEntity describe(@WebParam(name = "entityId", targetNamespace = "") String entityId) throws NoSuchEntityException, SessionExpiredException {
        return null;
    }

    @Override public Entity add(@WebParam(name = "entity", targetNamespace = "") Entity entity) throws InvalidEntityException, SessionExpiredException {
        entity.setId(currentIndex);
        currentIndex=currentIndex+1;
        entities.put(entity.getId(),entity);
        return entity;
    }
}
