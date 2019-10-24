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
            String condition = "hasRole('ADMIN') or hasRole('VET') or" + IsPetOwner.condition;
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

    public static class VeterinariansRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForAddVeterinarian.condition)
        public @interface AllowedForAddVeterinarian {
            String condition = "hasRole('ADMIN')";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForDeleteVeterinarian.condition)
        public @interface AllowedForDeleteVeterinarian {
            String condition = AllowedForAddVeterinarian.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForEditVeterinarian.condition)
        public @interface AllowedForEditVeterinarian {
            String condition = "@SecurityService.isVeterinarianAccountOwner(principal,#vetId)";
        }


        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetVeterinarian.condition)
        public @interface AllowedForGetVeterinarian {
            String condition = AllowedForAddVeterinarian.condition
                    + " or " + AllowedForEditVeterinarian.condition;
        }
    }

    public static class AdministratorsRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForDeleteAdministrador.condition)
        public @interface AllowedForDeleteAdministrador {
            String condition = "hasRole('ADMIN')";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForEditAdministrator.condition)
        public @interface AllowedForEditAdministrator {
            String condition = "@SecurityService.isAdministratorAccountOwner(principal,#adminId)";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetAdministrador.condition)
        public @interface AllowedForGetAdministrador {
            String condition = AllowedForDeleteAdministrador.condition;
        }
    }

    public static class SchedulesRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForEditSchedule.condition)
        public @interface AllowedForEditSchedule {
            String condition = "hasRole('ADMIN')";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetSchedule.condition)
        public @interface AllowedForGetSchedule {
            String condition = "hasRole('VET') or hasRole('CLIENT') or " + AllowedForEditSchedule.condition;
        }


    }


}

