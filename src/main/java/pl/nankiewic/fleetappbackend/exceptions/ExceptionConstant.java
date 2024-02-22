package pl.nankiewic.fleetappbackend.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstant {

    public static final String ENTITY_NOT_FOUND_ERROR = "entity.not.found.error";
    public static final String PERMISSION_DENIED_ERROR = "permission.denied.error";

}
