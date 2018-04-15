package ru.mezgin.tracker.model;

/**
 * The abstract class AbstractBaseEntity.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public abstract class AbstractBaseEntity {

    /**
     * Id.
     */
    protected Integer id;

    /**
     * Name.
     */
    protected String name;

    /**
     * The constructor.
     *
     * @param id   id.
     * @param name name.
     */
    protected AbstractBaseEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Verifies that the entity is new.
     *
     * @return true if entity is new otherwise false.
     */
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Get id.
     *
     * @return id.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Set id.
     *
     * @param id id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get name.
     *
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
