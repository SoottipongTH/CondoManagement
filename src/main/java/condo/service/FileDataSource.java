package condo.service;

import condo.model.Building;
import condo.model.Person;

import java.io.IOException;

public interface FileDataSource {
    boolean create(Object object) throws IOException;
}
