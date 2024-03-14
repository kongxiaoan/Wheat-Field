package com.kpa.localeflexpicker.base

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
class CountryOrRegionEntity(internalState: InternalState, externalState: ExternalState) : BaseSpell {
    private var internalState: InternalState? = internalState
    private var externalState: ExternalState? = externalState

    fun getInternalState(): InternalState? {
        return internalState
    }

    fun getName(): String {
        return internalState!!.name
    }

    fun getCode(): Int {
        return internalState!!.code
    }

    override fun getSpell(): String {
        return internalState!!.spell
    }

    override fun getHeaderSpell(): String {
        return internalState!!.headerSpell
    }

    override fun getIndexName(): String {
        return internalState!!.indexName
    }

    override fun getPriority(): Int {
        return internalState!!.priority
    }

    override fun isCommon(): Boolean {
        return externalState!!.isCommon
    }
}
