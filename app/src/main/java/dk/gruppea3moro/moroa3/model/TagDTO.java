package dk.gruppea3moro.moroa3.model;

public class TagDTO {
    private String category, id, text, formattedText;

    public static final String MOOD_CATEGORY = "stemning";
    public static final String TYPE_CATEGORY = "type";
    public static final String ZONE_CATEGORY = "zone";


    public TagDTO(String category, String id, String text, String formattedText) {
        this.category = category.toLowerCase();
        this.id = id.toLowerCase();
        this.text = text;
        this.formattedText = formattedText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.toLowerCase();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        //TODO tilføj mere kompliceret genkendelse af kategorien.
        String cat = category.toLowerCase();
        switch (cat){
            case MOOD_CATEGORY:
                this.category=MOOD_CATEGORY;
                break;
            case TYPE_CATEGORY:
                this.category=TYPE_CATEGORY;
                break;
            case ZONE_CATEGORY:
                this.category=ZONE_CATEGORY;
                break;
            default:
                System.out.println("ERROR - INCORRECT CATEGORY");
        }
    }
}
