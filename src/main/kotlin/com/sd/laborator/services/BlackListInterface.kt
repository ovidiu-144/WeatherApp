package com.sd.laborator.services

import com.sd.laborator.interfaces.BlackListInterface
import org.springframework.stereotype.Service

@Service
open class BlackListInterface : BlackListInterface {
    private val blackListedZones = listOf ("Russia", "Iran", "North Korea")

    override fun isZoneBlackListed(zoneName: String): Boolean {
        return blackListedZones.contains(zoneName)
    }

}