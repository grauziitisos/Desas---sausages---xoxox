package com.aal.bb

class desasState {
    // ja nebūtu default 0, varētu daudz vienkāršāk ar vienu db mainīgo...
    // if((db&0b1100100100)==0b1100100100) return desuZime.krusts;
    // if((db&0b1100100100)==0b1000000000) return desuZime.nulle;
    var db = 0

    // zeroNonDefaultZeroButDataZeroFlag;
    var n0 = 0
    var isHitterCross = false
    fun clear() {
        db = 0
        n0 = 0
    }

    fun g(): Array<Array<desuZime?>> {
        val answ = Array(3) {
            arrayOfNulls<desuZime>(
                3
            )
        }
        for (r in 0..2) for (c in 0..2) {
            if (db and (1 shl r * 3 + c) != 0) {
                answ[r][c] = desuZime.krusts
            }
            if (n0 and (1 shl r * 3 + c) != 0) answ[r][c] = desuZime.nulle
        }
        return answ
    }

    fun s(d: desuZime, r: Int, c: Int): Boolean {
        val getMaskForSetBit0_9bit = object : iAction_int_int {
            override fun s(zeroLocation: Int): Int {
                var answ = 0
                for (i in 0..8) {
                    if (i == zeroLocation) continue
                    answ = answ shl 1 or 1
                }
                return answ
            }
        }
        if (db and (1 shl r * 3 + c) != 0) return false
        if (n0 and (1 shl r * 3 + c) != 0) return false
        if (d === desuZime.krusts) {
            // set unclear state
            db = db or (1 shl 9)
            db = db or (1 shl r * 3 + c)
        } else {
            // set unclear state
            // db |= 1 << 9;
            // db &= getMaskForSetBit0_9bit.s((r * 3) + c);
            // what a shame..
            n0 = n0 or (1 shl 9)
            n0 = n0 or (1 shl r * 3 + c)
        }
        return true
    }

    // krusti
    // vert
    // hor
    // krusts
    val isVictory: desuZime?
        get() {
            // krusti
            // vert
            if ((db and 0b1100100100) == 0b1100100100)
                return desuZime.krusts
            if ((n0 and 0b1100100100) == 0b1100100100)
                return desuZime.nulle
            if ((db and 0b1010010010) == 0b1010010010)
                return desuZime.krusts
            if ((n0 and 0b1010010010) == 0b1010010010)
                return desuZime.nulle
            if ((db and 0b1001001001) == 0b1001001001)
                return desuZime.krusts
            if ((n0 and 0b1001001001) == 0b1001001001)
                return desuZime.nulle
            // hor
            if ((db and 0b1111000000) == 0b1111000000)
                return desuZime.krusts
            if ((db and 0b1000111000) == 0b1000111000)
                return desuZime.krusts
            if ((db and 0b1000000111) == 0b1000000111)
                return desuZime.krusts
            if ((n0 and 0b1111000000) == 0b1111000000)
                return desuZime.nulle
            if ((n0 and 0b1000111000) == 0b1000111000)
                return desuZime.nulle
            if ((n0 and 0b1000000111) == 0b1000000111)
                return desuZime.nulle
            // krusts
            if ((db and 0b1100010001) == 0b1100010001)
                return desuZime.krusts
            if ((db and 0b1001010100) == 0b1001010100)
                return desuZime.krusts
            if ((n0 and 0b1100010001) == 0b1100010001)
                return desuZime.nulle
            return if ((n0 and 0b1001010100) == 0b1001010100) desuZime.nulle else null
        }
    var rutis = inTupleTsorrynotypeparamThere(
        inCetruple(0, 0, 0, 0), inCetruple(0, 0, 0, 0)
    )

    fun setRutis(squares: inTupleTsorrynotypeparamThere): inTupleTsorrynotypeparamThere {
        rutis = squares
        return rutis
    }

    fun hit(x: Int, y: Int): Boolean {
        if (x < rutis.vertik.a || x > rutis.vertik.d || y < rutis.horiz.a || y > rutis.horiz.d) return false
        if (db or n0 == 1023) return false
        if (x >= rutis.vertik.a && x < rutis.vertik.b) {
            if (y >= rutis.horiz.a && y < rutis.horiz.b) {
                return if (isHitterCross) s(desuZime.krusts, 0, 0) else s(desuZime.nulle, 0, 0)
            }
            if (y >= rutis.horiz.b && y < rutis.horiz.c) {
                return if (isHitterCross) s(desuZime.krusts, 0, 1) else s(desuZime.nulle, 0, 1)
            }
            if (y >= rutis.horiz.c && y <= rutis.horiz.d) {
                return if (isHitterCross) s(desuZime.krusts, 0, 2) else s(desuZime.nulle, 0, 2)
            }
        } else if (x >= rutis.vertik.b && x < rutis.vertik.c) {
            if (y >= rutis.horiz.a && y < rutis.horiz.b) {
                return if (isHitterCross) s(desuZime.krusts, 1, 0) else s(desuZime.nulle, 1, 0)
            }
            if (y >= rutis.horiz.b && y < rutis.horiz.c) {
                return if (isHitterCross) s(desuZime.krusts, 1, 1) else s(desuZime.nulle, 1, 1)
            }
            if (y >= rutis.horiz.c && y <= rutis.horiz.d) {
                return if (isHitterCross) s(desuZime.krusts, 1, 2) else s(desuZime.nulle, 1, 2)
            }
        } else if (x >= rutis.vertik.c && x <= rutis.vertik.d) {
            if (y >= rutis.horiz.a && y < rutis.horiz.b) {
                return if (isHitterCross) s(desuZime.krusts, 2, 0) else s(desuZime.nulle, 2, 0)
            }
            if (y >= rutis.horiz.b && y < rutis.horiz.c) {
                return if (isHitterCross) s(desuZime.krusts, 2, 1) else s(desuZime.nulle, 2, 1)
            }
            if (y >= rutis.horiz.c && y <= rutis.horiz.d) {
                return if (isHitterCross) s(desuZime.krusts, 2, 2) else s(desuZime.nulle, 2, 2)
            }
        }
        return true
    }
}