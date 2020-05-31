package PDFUtil;

import java.util.List;

public class ObservablePdfFields {
    private String name, type, description, value, defaultValue, optionsStr;
    private List<String > options=null;

//  Construtor default
    public ObservablePdfFields(String name, String type, String description, String value){
        this.name=name;
        this.type=type;
        this.description=description;
        this.value=value;
        this.optionsStr=null;
    }
//  Construtor para campos CH
    public ObservablePdfFields(String name, String type, String description, String value, String defaultValue,
                               List<String> options){
        this.name=name;
        this.type=type;
        this.description=description;
        this.value=value;
        this.defaultValue=defaultValue;
        this.options= options;
        this.optionsStr=options.toString();
    }
//  Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public String getOptionsStr(){
        return optionsStr;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Field name: "+name+" type: "+type+" value: "+value;
    }
}
