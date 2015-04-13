package com.cookbook.tutorial.internal.service;

import com.cookbook.tutorial.internal.dsql.Constants;
import com.cookbook.tutorial.internal.dsql.CookBookQuery;
import com.cookbook.tutorial.internal.dsql.Dsql;
import com.cookbook.tutorial.internal.dsql.DsqlParser;
import com.cookbook.tutorial.service.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.parboiled.Parboiled;
import org.parboiled.common.FileUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.springframework.util.CollectionUtils;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Mulesoft.
 * <p/>
 * This is a dummy implementation, none persistent. Will be valid until the server stops running.
 */
public class CookBookDefaultBackEndImp implements IDAOCookBookService {

    private Integer currentIndex = 0;

    public CookBookDefaultBackEndImp() {
        initialize();
    }

    private void initialize() {

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Recipe>>() {

            }.getType();
            List<Recipe> recipes = gson.fromJson(FileUtils.readAllText(this.getClass().getClassLoader().getResourceAsStream("recipes.json")), listType);
            for(Recipe recipe : recipes){
                for(Ingredient ing:recipe.getIngredients()){
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(new Date());
                    ing.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                    this.create(ing);
                }
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(new Date());
                recipe.setCreated(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
                this.create(recipe);
            }
        } catch (InvalidEntityException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, CookBookEntity> entities = new HashMap<>();

    @Override
    public List<CookBookEntity> getList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds)
            throws NoSuchEntityException {
        List<CookBookEntity> returnValue = new ArrayList<>();
        for (Integer id : entityIds) {
            returnValue.add(get(id));
        }
        return returnValue;
    }

    @Override
    public void delete(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException {
        if (!entities.containsKey(id)) {
            throw new NoSuchEntityException();
        }
        entities.remove(id);
    }

    @Override
    public CookBookEntity create(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity) throws InvalidEntityException {
        if (entity.getId() != null) {
            FaultBean bean = ObjectFactorySingleton.getInstance().createFaultBean();
            bean.setEntity(entity);
            throw new InvalidEntityException("Cannot specify Id at creation.", bean);
        }
        entity.setId(++currentIndex);
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public CookBookEntity update(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity)
            throws NoSuchEntityException, InvalidEntityException {
        if (!entities.containsKey(entity.getId())) {
            throw new NoSuchEntityException();
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public CookBookEntity get(@WebParam(name = "id", targetNamespace = "") int id) throws NoSuchEntityException {

        if (!entities.containsKey(id)) {

            throw new NoSuchEntityException();
        }
        return entities.get(id);
    }

    @Override
    public List<CookBookEntity> updateList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws NoSuchEntityException, InvalidEntityException {
        for (CookBookEntity entity : entities) {
            update(entity);
        }
        return entities;
    }

    @Override
    public void deleteList(@WebParam(name = "entityIds", targetNamespace = "") List<Integer> entityIds) throws NoSuchEntityException {
        for (Integer id : entityIds) {
            delete(id);
        }
    }

    @Override
    public List<CookBookEntity> addList(@WebParam(name = "entities", targetNamespace = "") List<CookBookEntity> entities)
            throws InvalidEntityException {
        for (CookBookEntity entity : entities) {
            create(entity);
        }
        return entities;
    }

    @Override
    public List<CookBookEntity> searchWithQuery(@WebParam(name = "query", targetNamespace = "") String query, @WebParam(name = "page", targetNamespace = "") Integer page,
            @WebParam(name = "pageSize", targetNamespace = "") Integer pageSize) throws NoSuchEntityException {

        DsqlParser parser = Parboiled.createParser(DsqlParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.Statement()).run(query);
        if (result.hasErrors()) {
            throw new NoSuchEntityException(result.parseErrors.get(0).getErrorMessage());
        }
        List<CookBookEntity> searchResult = new ArrayList<>();
        CookBookQuery cookBookQuery = Dsql.newInstance(query);
        if (cookBookQuery.getEntity().equals(Constants.INGREDIENT)) {
            for (CookBookEntity entity : entities.values()) {
                if (entity instanceof Ingredient) {
                    searchResult.add(entity);
                }
            }
            return searchResult;
        } else if (cookBookQuery.getEntity().equals(Constants.RECIPE)) {
            for (CookBookEntity entity : entities.values()) {
                if (entity instanceof Recipe) {
                    searchResult.add(entity);
                }
            }
            return searchResult;
        }
        return CollectionUtils.arrayToList(entities.values().toArray());

    }

    @Override public List<Recipe> getRecentlyAdded() {
        Collection<CookBookEntity> values = entities.values();
        List<Recipe> recipies = new ArrayList<>();
        for (CookBookEntity entity : values) {
            if (entity instanceof Recipe) {
                recipies.add((Recipe) entity);
            }
        }
        return CollectionUtils.arrayToList(recipies.toArray());
    }

    @Override public Description describeEntity(@WebParam(name = "entity", targetNamespace = "") CookBookEntity entity)
            throws NoSuchEntityException, InvalidEntityException {
        Description description = null;
        if (entity.getId() == null || entity.getId() == 0) {
            if (entity instanceof Ingredient) {
                description = getIngredientDescription();
            } else {
                description = getRecipeDescription(entity.getId());
            }
        } else {
            if (entity instanceof Ingredient) {
                throw new InvalidEntityException("You cannot describe specific ingredients");
            } else {
                description = getRecipeDescription(entity.getId());
            }
        }
        return description;
    }

    @Override
    public List<CookBookEntity> getEntitiesList() {
        List<CookBookEntity> list = new ArrayList<>();
        Ingredient ing = ObjectFactorySingleton.getInstance().createIngredient();
        ing.setName("Ingredient");
        ing.setId(0);
        list.add(ing);
        Recipe recipe = new Recipe();
        recipe.setName("Recipe");
        recipe.setId(0);
        list.add(recipe);
        return list;
    }

    private Description getRecipeDescription(Integer id) throws NoSuchEntityException {
        Description description = ObjectFactorySingleton.getInstance().createDescription();
        List<Description> fields = new ArrayList<>();
        description.setName("Recipe");
        populateCookBookEntityFields(description, fields);
        addRecipeIngredientFields(fields, id);
        description.setInnerFields(fields);
        return description;
    }

    private void addRecipeIngredientFields(List<Description> fields, Integer id) throws NoSuchEntityException {

        Description field;

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("ingredients");
        field.setDataType(DataType.LIST);
        field.setQuerable(true);
        field.setSortable(true);
        field.setInnerType("Ingredient");
        fields.add(field);
        if (id != null && id != 0) {
            loadRecipeFields(field, id);
        } else {
            Description ingredient = getIngredientDescription();
            List<Description> innerFields = new ArrayList<>(1);
            innerFields.add(ingredient);
            field.setInnerFields(innerFields);
        }

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("prepTime");
        field.setDataType(DataType.DOUBLE);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("cookTime");
        field.setDataType(DataType.DOUBLE);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("directions");
        field.setDataType(DataType.LIST);
        field.setQuerable(false);
        field.setSortable(false);
        field.setInnerType("String");
        fields.add(field);
    }

    private void loadRecipeFields(Description field, Integer id) throws NoSuchEntityException {
        Recipe recipe = (Recipe) this.get(id);
        List<Description> innerFields = new ArrayList<>(recipe.getIngredients().size());
        for (Ingredient ing : recipe.getIngredients()) {
            Description description = getIngredientDescription();
            description.setName(ing.getName());
            description.setQuerable(false);
            description.setSortable(false);
            innerFields.add(description);
        }
        field.setInnerFields(innerFields);
    }

    private Description getIngredientDescription() {
        Description description = ObjectFactorySingleton.getInstance().createDescription();
        List<Description> fields = new ArrayList<>();
        description.setName("Ingredient");
        populateCookBookEntityFields(description, fields);
        addSpecificIngredientFields(fields);
        description.setInnerFields(fields);
        return description;
    }

    private void addSpecificIngredientFields(List<Description> fields) {
        Description field;
        /*
        private double quantity;
        private UnitType unit;
        */
        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("quantity");
        field.setDataType(DataType.DOUBLE);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("unit");
        field.setDataType(DataType.UNIT_TYPE);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);
    }

    /**
     * Populate common CookBookEntity fields
     *
     * @param description The object representing the description of the CookBookEntity
     * @param fields      The list of fields we have to populate.
     */
    private void populateCookBookEntityFields(Description description, List<Description> fields) {
        description.setDataType(DataType.OBJECT);
        description.setQuerable(true);
        description.setSortable(false);

        Description field = null;

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("id");
        field.setDataType(DataType.INTEGER);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("created");
        field.setDataType(DataType.DATE);
        field.setQuerable(false);
        field.setSortable(false);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("lastModified");
        field.setDataType(DataType.DATE);
        field.setQuerable(false);
        field.setSortable(false);
        fields.add(field);

        field = ObjectFactorySingleton.getInstance().createDescription();
        field.setName("name");
        field.setDataType(DataType.STRING);
        field.setQuerable(true);
        field.setSortable(true);
        fields.add(field);
    }
}
