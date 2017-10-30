package cz.fi.muni.pa165;

import cz.fi.muni.pa165.entities.Airplane;

class AirplaneBuilder {

    private Airplane airplane;

    AirplaneBuilder() {
        airplane = new Airplane();
    }

    AirplaneBuilder setId(Long id) {
        airplane.setId(id);
        return this;
    }

    AirplaneBuilder setType(String type) {
        airplane.setType(type);
        return this;
    }

    AirplaneBuilder setName(String name) {
        airplane.setName(name);
        return this;
    }

    AirplaneBuilder setCapacity(int capacity) {
        airplane.setCapacity(capacity);
        return this;
    }

    Airplane build() {
        return airplane;
    }
}
