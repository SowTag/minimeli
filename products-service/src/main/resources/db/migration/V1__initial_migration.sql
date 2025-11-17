CREATE TABLE attribute_value
(
    id            UUID NOT NULL,
    attribute_id  UUID NOT NULL,
    string_value  VARCHAR(255),
    long_value    BIGINT,
    double_value  DOUBLE PRECISION,
    boolean_value BOOLEAN,
    CONSTRAINT pk_attribute_value PRIMARY KEY (id)
);

CREATE TABLE category
(
    id                 UUID NOT NULL,
    title              VARCHAR(255),
    description        VARCHAR(255),
    parent_category_id UUID,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE category_path
(
    id            UUID    NOT NULL,
    ancestor_id   UUID    NOT NULL,
    descendant_id UUID    NOT NULL,
    depth         INTEGER NOT NULL,
    CONSTRAINT pk_category_path PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          UUID NOT NULL,
    title       OID  NOT NULL,
    description OID,
    category_id UUID,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_attribute
(
    id UUID NOT NULL,
    CONSTRAINT pk_product_attribute PRIMARY KEY (id)
);

ALTER TABLE attribute_value
    ADD CONSTRAINT FK_ATTRIBUTE_VALUE_ON_ATTRIBUTE FOREIGN KEY (attribute_id) REFERENCES product_attribute (id);

ALTER TABLE category
    ADD CONSTRAINT FK_CATEGORY_ON_PARENT_CATEGORY FOREIGN KEY (parent_category_id) REFERENCES category (id);

ALTER TABLE category_path
    ADD CONSTRAINT FK_CATEGORY_PATH_ON_ANCESTOR FOREIGN KEY (ancestor_id) REFERENCES category (id);

CREATE INDEX id_category_path_ancestor ON category_path (ancestor_id);

ALTER TABLE category_path
    ADD CONSTRAINT FK_CATEGORY_PATH_ON_DESCENDANT FOREIGN KEY (descendant_id) REFERENCES category (id);

CREATE INDEX id_category_path_descendant ON category_path (descendant_id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);