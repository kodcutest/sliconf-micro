package javaday.istanbul.sliconf.micro.specs;

import javaday.istanbul.sliconf.micro.model.event.About;
import javaday.istanbul.sliconf.micro.model.event.Event;
import javaday.istanbul.sliconf.micro.model.event.StatusDetails;
import javaday.istanbul.sliconf.micro.service.event.EventRepositoryService;
import javaday.istanbul.sliconf.micro.util.Constants;
import javaday.istanbul.sliconf.micro.util.RandomGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Event ile ilgili cok kullanilan isleri barindiran sinif
 */
public class EventSpecs {

    private EventSpecs() {
        // private constructor for static
    }

    /**
     * Event name'in minimum uzunlugunu gelen parametreye gore kontrol eder
     *
     * @param event
     * @param nameLength
     * @return
     */
    public static boolean checkEventName(Event event, int nameLength) {
        return event.getName().length() >= nameLength;
    }

    /**
     * Olusturulan eventin tarihi geri donuk olmamali
     *
     * @param event
     * @return
     */
    public static boolean checkIfEventDateAfterOrInNow(Event event) {
        LocalDateTime now = LocalDateTime.now();

        if (Objects.nonNull(event) && Objects.nonNull(event.getStartDate())) {
            return now.isBefore(event.getStartDate());
        }

        return false;
    }

    /**
     * Event'in tarihi verilen tarihten sonra mi kontrolu
     *
     * @param event
     * @return
     */
    public static boolean checkIfEventDateAfterFromGivenDate(Event event, LocalDateTime dateTime) {
        return dateTime.isBefore(event.getStartDate());
    }

    public static String generateKanbanNumber(Event event, EventRepositoryService eventRepositoryService) {
        boolean isKeyUnique = false;
        String key;

        while (!isKeyUnique) {
            key = RandomGenerator.generateRandom(Constants.EVENT_KEY_LENGTH);

            if (!eventRepositoryService.isKeyExists(key)) {
                event.setKey(key);
                isKeyUnique = true;
            }
        }

        return event.getKey();
    }

    /**
     * Event icin verilmesi gereken bilgileri kontrol eder ve bu bilgilerin olup olmamasina gore
     * statusu true ya da false ve girilmis degerler ve girilmememis degerleri belirtir
     *
     * @param event
     */
    public static void generateStatusDetails(Event event) {
        if (Objects.nonNull(event)) {

            StatusDetails statusDetails = new StatusDetails();

            List<String> failed = new ArrayList<>();
            List<String> passed = new ArrayList<>();

            List<String> optionalFailed = new ArrayList<>();
            List<String> optionalPassed = new ArrayList<>();

            checkRequeiredFields(event, passed, failed);

            checkOptionalFields(event, optionalPassed, optionalFailed);

            statusDetails.setPassed(passed);
            statusDetails.setFailed(failed);

            statusDetails.setOptionalPassed(optionalPassed);
            statusDetails.setOptionalFailed(optionalFailed);

            int count = passed.size() + failed.size();
            int percentage = (int) (passed.size() / ((double) count) * 100);
            statusDetails.setPercentage(percentage);

            event.setStatusDetails(statusDetails);

            if (percentage >= 100) {
                event.setStatus(true);
            } else {
                event.setStatus(false);
            }
        }
    }

    /**
     * Bir kat, bir oda, bir konuşmacı, bir konuşma, tarih, etkinlik ismi, lokasyon zorunlu.
     *
     * @param event
     * @param passed
     * @param failed
     */
    private static void checkRequeiredFields(Event event, List<String> passed, List<String> failed) {
        if (Objects.nonNull(event)) {
            if (Objects.isNull(event.getFloorPlan()) || event.getFloorPlan().isEmpty()) {
                failed.add("At least one floor must be added");
            } else {
                passed.add("At least one floor added");
            }

            if (Objects.isNull(event.getRooms()) || event.getRooms().isEmpty()) {
                failed.add("At least one room must be added");
            } else {
                passed.add("At least one room added");
            }

            if (Objects.isNull(event.getSpeakers()) || event.getSpeakers().isEmpty()) {
                failed.add("At least one speaker must be added");
            } else {
                passed.add("At least one speaker added");
            }

            if (Objects.isNull(event.getAgenda()) || event.getAgenda().isEmpty()) {
                failed.add("At least one agenda must be added");
            } else {
                passed.add("At least one agenda added");
            }

            if (Objects.isNull(event.getStartDate())) {
                failed.add("Start date must be added");
            } else {
                passed.add("Start date added");
            }

            if (Objects.isNull(event.getName()) || event.getName().isEmpty()) {
                failed.add("Event date must be added");
            } else {
                passed.add("Event name added");
            }

            if (Objects.isNull(event.getAbout()) || Objects.isNull(event.getAbout().getLocation()) ||
                    Objects.isNull(event.getAbout().getLocation().getDescription()) ||
                    Objects.isNull(event.getAbout().getLocation().getLat()) ||
                    Objects.isNull(event.getAbout().getLocation().getLng()) ||
                    event.getAbout().getLocation().getDescription().isEmpty() ||
                    event.getAbout().getLocation().getLng().isEmpty() ||
                    event.getAbout().getLocation().getLat().isEmpty()) {
                failed.add("Event location must be filled correctly");
            } else {
                passed.add("Event location added");
            }
        }
    }

    /**
     * Etkinlik aciklamasi, logo, sosyal alanlar, web sitesi, telefon numarası, e-mail istege bagli.
     *
     * @param event
     * @param optionalPassed
     * @param optionalFailed
     */
    private static void checkOptionalFields(Event event, List<String> optionalPassed, List<String> optionalFailed) {
        if (Objects.nonNull(event)) {
            if (Objects.isNull(event.getDescription()) || event.getDescription().isEmpty()) {
                optionalFailed.add("Event description can be added");
            } else {
                optionalPassed.add("Event description added");
            }

            if (Objects.isNull(event.getLogoPath()) || event.getLogoPath().isEmpty()) {
                optionalFailed.add("Event logo can be added");
            } else {
                optionalPassed.add("Logo added");
            }

            if (Objects.nonNull(event.getAbout())) {

                About about = event.getAbout();

                if (Objects.isNull(about.getSocial()) || about.getSocial().isEmpty()) {
                    optionalFailed.add("Social details can be added");
                } else {
                    optionalPassed.add("Social details added");
                }

                if (Objects.isNull(about.getWeb()) || about.getWeb().isEmpty()) {
                    optionalFailed.add("Web site address can be added");
                } else {
                    optionalPassed.add("Web site address added");
                }

                if (Objects.isNull(about.getPhone()) || about.getPhone().isEmpty()) {
                    optionalFailed.add("Phone can be added");
                } else {
                    optionalPassed.add("Phone added");
                }

                if (Objects.isNull(about.getEmail()) || about.getEmail().isEmpty()) {
                    optionalFailed.add("E-mail address can be added");
                } else {
                    optionalPassed.add("E-mail address added");
                }
            } else {
                optionalFailed.add("Social details can be added");
                optionalFailed.add("Web site address can be added");
                optionalFailed.add("Phone can be added");
                optionalFailed.add("E-mail address can be added");
            }
        }
    }
}
