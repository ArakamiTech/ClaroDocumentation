package co.com.cat.clarodocumentation.enums;

public enum Enum {

	BLACK_COLOR("000000"), COLOR("2f66f2"), CREATOR("Cristhian Torres"), CREATOR_DESCRIPTION("Creador del documento"),
	DATE("Fecha"), DESCRIPTION("Descripción"), DESCRIPTION_FILE("Archivos excel (.xlsx)"), ERROR("Error {}"),
	FILE("Documentos"), GREATER_SYMBOL(">"), INITIAL_ROUTE("documentos.docx"), LESS_SYMBOL("<"), LONG("Long"),
	NAME("Nombre"), NUMBER("number"), OPERATION("Operación: "), ORDER("N° "), PATH("Path: "), REQUEST("Request"),
	REQUEST_VALUES("Campos Request: "), RESPONSE("Response"), RESPONSE_ERROR("Response Errado"),
	SELECTED_NONE("No se ha seleccionado ningún fichero"), SERVICE("Servicio "), SPACE_NONE(""),
	SUBTITLE("Bitácora de cambios:"), SUCCESSFULL_RESPONSE("Campos Response Exitoso"), TEXT_PATH("PATH:"),
	TYPE_FILE("xlsx"), TITLE("Manual de usuario o de consumo de microservicio"), TYPE("Tipo"), URL("URL base: "),
	VERB("Verbo: ");

	private String value;

	Enum(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

}
