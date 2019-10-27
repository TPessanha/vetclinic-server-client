package personal.ciai.vetclinic.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

public class AccessControlRules {
    public static class UserRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(IsPrincipalAccountOwner.condition)
        public @interface IsPrincipalAccountOwner {
            String condition = "@SecurityService.isPrincipalAccountOwner(principal,#clientId)";
        }
    }

    public static class ClientRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetClientAppointments.condition)
        public @interface AllowedForGetClientAppointments {
            String condition = "hasRole('ADMIN') or " + UserRules.IsPrincipalAccountOwner.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetClientDetails.condition)
        public @interface AllowedForGetClientDetails {
            String condition = "hasRole('ADMIN') or " + UserRules.IsPrincipalAccountOwner.condition;
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForEditClientDetails.condition)
        public @interface AllowedForEditClientDetails {
            String condition = UserRules.IsPrincipalAccountOwner.condition;
        }
    }

    public static class PetsRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetPetAppointments.condition)
        public @interface AllowedForGetPetAppointments {
            String condition = "hasRole('ADMIN') or " + IsPetOwner.condition;
        }

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
                "hasRole('ADMIN') or " + IsPetOwner.condition + " and " + UserRules.IsPrincipalAccountOwner.condition;
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
            String condition = "hasRole('ADMIN') or hasRole('VET') or " + UserRules.IsPrincipalAccountOwner.condition;
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
        @PreAuthorize(AllowedForGetAllSchedules.condition)
        public @interface AllowedForGetAllSchedules {
            String condition = "isAuthenticated()";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetSchedule.condition)
        public @interface AllowedForGetSchedule {
            String condition = "isAuthenticated()";
        }
    }

    public static class AppointmentRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetOneAppointment.condition)
        public @interface AllowedForGetOneAppointment {
            String condition =
                "hasRole('ADMIN') or hasRole('VET') or (" +
                    UserRules.IsPrincipalAccountOwner.condition + " and " + PetsRules.IsPetOwner.condition +
                    ")";
        }

        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForAddAppointment.condition)
        public @interface AllowedForAddAppointment {
            String condition = PetsRules.IsPetOwner.condition + " and " + UserRules.IsPrincipalAccountOwner.condition;
        }
    }

    public static class NotificationRules {
        @Target({ElementType.METHOD, ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Inherited
        @Documented
        @PreAuthorize(AllowedForGetNotification.condition)
        public @interface AllowedForGetNotification {
            String condition = "hasRole('CLIENT') and " + "@SecurityService.isPrincipalWithID(principal,#id)";
        }
    }
}

