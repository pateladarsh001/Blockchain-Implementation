import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleTree {

    int level =0;

    public String buildFrom(Transaction[] transactions) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String[] leafHashes = new String[transactions.length];//This array holds the hashes of the leaf nodes of the tree
        for(int i=0;i<transactions.length;i=i+2){
            Transaction tx1 = transactions[i];
            leafHashes[i] =UtilityFunctions.getSHA256(digest, tx1.toString());//Find the hashes of each of the leaf nodes
        }

        String topHash=getTopHash(leafHashes);//Call the helped recursive function to obtain the topHash
        System.out.println( "Merkle top hash is: "+topHash);
        return topHash;
    }

    /**
     * recursive function
     * @param hashesArray is an array of hashes of the child nodes(transactions)
     * @return the topHash of the merkle tree
     * @throws NoSuchAlgorithmException
     */
    private String getTopHash(String[] hashesArray) throws NoSuchAlgorithmException {
        String returnString = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        System.out.println("Merkle Tree, Bottom Up, Level: "+(level++)+", number of hashes: "+hashesArray.length);//Print the stats
        if(hashesArray.length>=2){//Our condition we will use for recursion
            String[] hashes = new String[hashesArray.length/2];//the new array of new hashes of parent nodes of the tree
            for (int i = 0; i<hashes.length;i++){
                hashes[i] = UtilityFunctions.getSHA256(digest, hashesArray[i] + hashesArray[i*2]);
            }
            returnString = getTopHash(hashes);
        }else{//If the array has only one hash string then return that
            returnString = hashesArray[0];
        }
        return returnString;
    }

}
