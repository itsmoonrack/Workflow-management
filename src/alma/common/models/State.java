package alma.common.models;

public enum State {
	RAW, //Article de base.
	IDENTIFIED, //Une fois que l'article à reçut un identificateur.
	EDIT_VALIDATED, //Une fois l'article édité et validé par un correcteur.
	PUBLISHED //Une fois l'article publié.
}
