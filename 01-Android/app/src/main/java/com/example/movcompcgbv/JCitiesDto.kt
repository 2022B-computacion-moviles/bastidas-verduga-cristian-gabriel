package com.example.movcompcgbv

class JCitiesDto(
    public var name: String?,
    public var state: String?,
    public var country: String?,
    public var capital: Boolean?,
    public var population: Long?,
    public var regions: List<String?>?
) {
    override fun toString(): String {
        return "JCitiesDto(name=$name, state=$state, country=$country, capital=$capital, population=$population, regions=$regions)"
    }
}
