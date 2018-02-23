import java.math.BigInteger

fun main (args: Array<String>){

    //---------Shiz vertibas var mainit----------
    val privateKey = arrayListOf(1, 2, 4, 18, 38, 94)
    val byteArray = byteArrayOf(0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0)
    val dzimDiena = BigInteger.valueOf(10)
    val primNumb = BigInteger.valueOf(211)
    //-------------------------------------------

    val convertArray = privateKey.map { it.toBigInteger() } as ArrayList<BigInteger>
    val publicKey = getPublicKey(convertArray, dzimDiena, primNumb)

    println("privateKey: $privateKey [$dzimDiena, $primNumb]")
    println("bytePlain: ${byteArray.toMutableList()}") //pagaidu var, lai izprintetu
    println("publicKey: $publicKey")
    val cryptedValue = criptedText(byteArray, publicKey)
    //println("------------------------------")
    println("cryptedText: $cryptedValue")
    var preDecryptedValues = preDecyptedValues(cryptedValue, dzimDiena, primNumb)
    println("preDecryptedValues: $preDecryptedValues")
    val dectyptedText = decryptedText(preDecryptedValues, convertArray)
    println("decryptedText: $dectyptedText")
    println("originalText:  ${byteArray.toMutableList()}")

}


fun decryptedText(preDecryptedValues: ArrayList<BigInteger>, privateKey: ArrayList<BigInteger>): ArrayList<Byte> {
    println("-----Parbaude-----")
    privateKey.reverse()
    var sumValue = BigInteger.valueOf(0)
    val byteArray: ArrayList<Byte> = ArrayList()
    val tempByteArray: ArrayList<Byte> =  ArrayList()
    for (b in preDecryptedValues){ // [9, 70, 48]
        for (m in privateKey) { // [52, 27, 13, 6, 3, 2]
            if (sumValue != b) { //parbauda vai var ielikt ieksha 9 vel kaut ko
                if ((b-m) >= BigInteger.valueOf(0)) { //vai 9-51 >= 0
                    if ((sumValue+m) <= b) { //52+27 <= 70
                        tempByteArray.add(1)
                        print("Parbaude: $sumValue + $m = ")
                        sumValue += m //pieskaita klat 6
                        println("$sumValue")
                    } else { tempByteArray.add(0) } //52+27 > 70 (b)
                } else { tempByteArray.add(0) } //rodas negativs skaitlis
            } else { tempByteArray.add(0) } // sumValue = 9, tpc visi parejie ir 0
            //sumValue -= sumValue //nepareizi vietaa ielikts. Nulle ik pec katra
        }
        tempByteArray.reverse()
        byteArray += tempByteArray
        tempByteArray.reverse()
        tempByteArray.clear()
        println("--------------")
        sumValue -= sumValue
    }
    return byteArray
}

fun criptedText(byteArray: ByteArray, publicKeyArray: ArrayList<BigInteger>): ArrayList<BigInteger>{
    val byteLength = byteArray.size
    val publicKeyLength = publicKeyArray.size

    val one: Byte = 1
    val fullArraySize = byteLength/publicKeyLength
    val fullPublicKey: List<BigInteger> = publicKeyArray.times(fullArraySize)
    println("fullPublicKey: $fullPublicKey")

    //val crText = arrayListOf(BigInteger.valueOf(0))
    //crText.removeAt(0) //shity coding :D
    val crText: ArrayList<BigInteger> = ArrayList()

        var indexValue = 0 //priekš byarry, kas ir = 1
        var indexValueFull = 0

        var tempPublicKeyLength = publicKeyLength - 1 //jo sanaktu 6 bez -1, index saakas no 0
        var value: BigInteger = BigInteger.valueOf(0)
        for (b in byteArray) {
            if (b == one) {
                value += fullPublicKey[indexValue]
                //print("$value ")
            }
            if (indexValueFull == tempPublicKeyLength){
                crText.add(value)
                tempPublicKeyLength += publicKeyLength //nav jazimanto atņemšana, jo sakot no 6 nav 0
                //println("$tempPublicKeyLength ")
                value = BigInteger.valueOf(0)
            }
            indexValue++
            indexValueFull++
        }
    //println("cryptedText: $crText")
    return crText
}

fun getPublicKey(privateKey: ArrayList<BigInteger>, birthDay: BigInteger, primNumb: BigInteger): ArrayList<BigInteger> {
    //val inversMod = birthDay.modInverse(primNumb)
    val publicKey: ArrayList<BigInteger> = ArrayList()
        privateKey.mapTo(publicKey) { (it * birthDay).mod(primNumb) }
    return publicKey
}

fun preDecyptedValues(cryptedArray: ArrayList<BigInteger>, birthDay: BigInteger, primNumb: BigInteger): ArrayList<BigInteger> {
    val inversMod = birthDay.modInverse(primNumb) //butu labaja nodefinet global mainigo
    //println(inversMod)
    val decrypltedValues: ArrayList<BigInteger> = ArrayList()
    for (m in cryptedArray){
        decrypltedValues.add((m*inversMod).mod(primNumb))
    }
    return decrypltedValues
}

fun <T> Iterable<T>.times(count: Int) = (1..count).flatMap { this } //funkcija, lai izmantoto .times priekš array