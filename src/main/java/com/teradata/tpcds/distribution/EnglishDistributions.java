/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teradata.tpcds.distribution;

import com.teradata.tpcds.random.RandomNumberStream;

import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public final class EnglishDistributions
{
    private static final StringValuesDistribution ADJECTIVES_DISTRIBUTION = buildStringValuesDistribution("adjectives.dst", 1, 1);
    private static final StringValuesDistribution ADVERBS_DISTRIBUTION = buildStringValuesDistribution("adverbs.dst", 1, 1);
    private static final StringValuesDistribution ARTICLES_DISTRIBUTION = buildStringValuesDistribution("articles.dst", 1, 1);
    private static final StringValuesDistribution AUXILIARIES_DISTRIBUTION = buildStringValuesDistribution("auxiliaries.dst", 1, 1);
    private static final StringValuesDistribution PREPOSITIONS_DISTRIBUTION = buildStringValuesDistribution("prepositions.dst", 1, 1);
    private static final StringValuesDistribution NOUNS_DISTRIBUTION = buildStringValuesDistribution("nouns.dst", 1, 1);
    private static final StringValuesDistribution SENTENCES_DISTRIBUTION = buildStringValuesDistribution("sentences.dst", 1, 1);
    public static final StringValuesDistribution SYLLABLES_DISTRIBUTION = buildStringValuesDistribution("syllables.dst", 1, 1);
    private static final StringValuesDistribution TERMINATORS_DISTRIBUTION = buildStringValuesDistribution("terminators.dst", 1, 1);
    private static final StringValuesDistribution VERBS_DISTRIBUTION = buildStringValuesDistribution("verbs.dst", 1, 1);

    private EnglishDistributions() {}

    public static String pickRandomAdjective(RandomNumberStream stream)
    {
        return ADJECTIVES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomAdverb(RandomNumberStream stream)
    {
        return ADVERBS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomArticle(RandomNumberStream stream)
    {
        return ARTICLES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomAuxiliary(RandomNumberStream stream)
    {
        return AUXILIARIES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomNoun(RandomNumberStream stream)
    {
        return NOUNS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomPreposition(RandomNumberStream stream)
    {
        return PREPOSITIONS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomSentence(RandomNumberStream stream)
    {
        return SENTENCES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static int getSyllablesSize()
    {
        return SYLLABLES_DISTRIBUTION.getSize();
    }

    public static String getSyllableAtIndex(int index)
    {
        return SYLLABLES_DISTRIBUTION.getValueAtIndex(0, index);
    }

    public static String pickRandomTerminator(RandomNumberStream stream)
    {
        return TERMINATORS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomVerb(RandomNumberStream stream)
    {
        return VERBS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }
}
