package top.zfmx;

import org.junit.Test;
import top.zfmx.gene.Gene;
import top.zfmx.search.SearchFactory;
import top.zfmx.search.strategy.SearchStrategy;

public class TestGene {

    @Test
    public void testLinearSearch() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";

        Gene myGene = new Gene(geneStr);
        Gene.Codon acg = new Gene.Codon("ACG");
        Gene.Codon gat = new Gene.Codon("GAT");

        System.out.println(myGene.linearContains(acg)); // true
        assert myGene.linearContains(acg);

        System.out.println(myGene.linearContains(gat)); // false
        assert !myGene.linearContains(gat);
    }

    @Test
    public void testBinarySearch() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";

        Gene myGene = new Gene(geneStr);
        Gene.Codon acg = new Gene.Codon("ACG");
        Gene.Codon gat = new Gene.Codon("GAT");

        System.out.println(myGene.binaryContains(acg)); // true
        assert myGene.binaryContains(acg);

        System.out.println(myGene.binaryContains(gat)); // false
        assert !myGene.binaryContains(gat);
    }

    @Test
    public void testGeneFactory() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";

        Gene myGene = new Gene(geneStr);
        Gene.Codon acg = new Gene.Codon("ACG");
        Gene.Codon gat = new Gene.Codon("GAT");

        SearchFactory searchFactory = new SearchFactory();
        SearchStrategy<Gene.Codon> linearSearch = searchFactory.getSearchStrategy("linear");
        SearchStrategy<Gene.Codon> binarySearch = searchFactory.getSearchStrategy("binary");

        System.out.println(linearSearch.contains(myGene.codons, acg)); // true
        assert linearSearch.contains(myGene.codons, acg);

        System.out.println(linearSearch.contains(myGene.codons, gat)); // false
        assert !linearSearch.contains(myGene.codons, gat);

        System.out.println(binarySearch.contains(myGene.codons, acg)); // true
        assert binarySearch.contains(myGene.codons, acg);

        System.out.println(binarySearch.contains(myGene.codons, gat)); // false
        assert !binarySearch.contains(myGene.codons, gat);
    }
}
