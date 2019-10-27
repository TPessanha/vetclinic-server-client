package personal.ciai.vetclinic.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

public class AccessControlRules {
    public static class UserRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(IsPrincipalTheClient.condition)
        public @interface IsPrincipalTheClient {
            String condition = "@SecurityService.isPrincipalWithID(principal,#clientId)";
        }
    }

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
        @PreAuthorize(AllowedForEditPet.condition)
        public @interface AllowedForEditPet {
            String condition =
                "hasRole('ADMIN') or " + IsPetOwner.condition + " and " + UserRules.IsPrincipalTheClient.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetOnePet.condition)
        public @interface AllowedForGetOnePet {
            String condition = "hasRole('ADMIN') or hasRole('VET') or " + IsPetOwner.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetClientPets.condition)
        public @interface AllowedForGetClientPets {
            String condition = "hasRole('ADMIN') or hasRole('VET') or " + UserRules.IsPrincipalTheClient.condition;
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
        @PreAuthorize(AllowedForDeleteAdministrators.condition)
        public @interface AllowedForDeleteAdministrators {
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
            String condition = "hasRole('ADMIN')";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForAddAdministrator.condition)
        public @interface AllowedForAddAdministrator {
            String condition = "hasRole('ADMIN')";
        }
    }

    public static class ScheduleRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForAddSchedule.condition)
        public @interface AllowedForEditSchedule {
            String condition = AllowedForEditSchedule.condition + " or " + "@SecurityService.isVeterinarianAccountOwner(principal,#adminId)";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForAddSchedule.condition)
        public @interface AllowedForAddSchedule {
            String condition = "hasRole('ADMIN')";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetSchedule.condition)
        public @interface AllowedForGetSchedule {
            String condition = "hasRole('VET') or hasRole('CLIENT') or " + AllowedForAddSchedule.condition;
        }
    }





}

