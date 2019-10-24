package personal.ciai.vetclinic.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

public class AccessControlRules {
    public static class PetsRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(IsPetOwner.condition)
        public @interface IsPetOwner {
            String condition = "@SecurityService.isPetOwner(principal,#id)";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetPet.condition)
        public @interface AllowedForGetPet {
            String condition = "hasRole('ROLE_ADMIN') or " + IsPetOwner.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(IsPrincipalTheClient.condition)
        public @interface IsPrincipalTheClient {
            String condition = "@SecurityService.isPrincipalWithID(principal,#clientId)";
        }
    }


}

