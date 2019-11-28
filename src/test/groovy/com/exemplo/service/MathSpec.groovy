package com.exemplo.service

import spock.lang.Specification
import spock.lang.Unroll

class MathSpec extends Specification{


    def 'retorna maior entre dois números'(){
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3, 4]
        b << [1, 9, 5]
        c << [5, 9, 5]
    }

    @Unroll
    def 'retorna maior entre #a e #b '(){
        expect:
        Math.max(a, b) == c

        where:
        a | b | c
        5 | 3 | 5
        1 | 9 | 9
        5 | 4 | 5
    }

}
