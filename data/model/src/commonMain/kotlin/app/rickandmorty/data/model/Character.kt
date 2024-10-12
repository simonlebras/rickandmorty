package app.rickandmorty.data.model

public data class Character(
    val id: String,
    val name: String,
    val status: Status,
    val species: Species,
    val type: String,
    val gender: Gender,
    val image: String,
) {
    public enum class Status {
        Alive,
        Dead,
        Unknown,
        ;

        public companion object {
            public fun from(value: String): Status = entries
                .firstOrNull { it.name.equals(value, true) }
                ?: Unknown
        }
    }

    public enum class Species {
        Alien,
        Human,
        Humanoid,
        Other,
        ;

        public companion object {
            public fun from(value: String): Species = entries
                .firstOrNull { it.name.equals(value, true) }
                ?: Other
        }
    }

    public enum class Gender {
        Female,
        Male,
        Genderless,
        Unknown,
        ;

        public companion object {
            public fun from(value: String): Gender = entries
                .firstOrNull { it.name.equals(value, true) }
                ?: Unknown
        }
    }
}
