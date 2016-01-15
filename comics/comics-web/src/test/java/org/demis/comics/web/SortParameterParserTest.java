package org.demis.comics.web;

import org.demis.comics.data.SortParameterElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SortParameterParserTest {

    @Test
    public void sortAscendantParameterParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse("attribute");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 1);
        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortDescendantParameterParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse("-attribute");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 1);
        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute");
        Assert.assertEquals(sort.isAscending(), false);
    }

    @Test
    public void sortParametersParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse("-attribute1|attribute2");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortEmptyParameterParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse("-attribute1||attribute2");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortEmptyParameterFirstParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse("|-attribute1||attribute2|");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortParametersWithSpaceParsing() {
        List<SortParameterElement> sorts = SortParameterParser.parse(" - attribute1 | attribute2 ");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }

    @Test
    public void sortParametersParsingPlus() {
        List<SortParameterElement> sorts = SortParameterParser.parse("-attribute1|+attribute2");
        Assert.assertNotNull(sorts);
        Assert.assertEquals(sorts.size(), 2);

        SortParameterElement sort = sorts.get(0);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute1");
        Assert.assertEquals(sort.isAscending(), false);

        sort = sorts.get(1);
        Assert.assertNotNull(sort);
        Assert.assertEquals(sort.getProperty(), "attribute2");
        Assert.assertEquals(sort.isAscending(), true);
    }


}
