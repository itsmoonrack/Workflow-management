package alma.common.models;

public enum State {
	RAW, //Article de base.
	DISPATCHED, //Une fois que l'article à reçut un identificateur.
	RELEASED, //Une fois l'article édité et validé par un correcteur.
	PUBLISHED //Une fois l'article publié.
}
