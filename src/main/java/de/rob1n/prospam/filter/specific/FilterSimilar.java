package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.ChatMessage;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.filter.Filter;

import java.util.Date;
import java.util.List;

public class FilterSimilar extends Filter
{

	public FilterSimilar(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	protected String executeFilter(final Chatter chatter, final String message)
	{
		final long timeNow = (new Date()).getTime();
		
		final List<ChatMessage> previousMessages = chatter.getMessages();
		for(ChatMessage previousMessage: previousMessages)
		{
			if((timeNow - previousMessage.timeSubmitted)/1000L <= settings.filter_lines_similar)
			{
				//StringUtils.getLevenshteinDistance(message, previousMessage.message)
				if(LevenshteinDistance.computeLevenshteinDistance(message, previousMessage.message) <= message.length()/4+1)
					return null;
			}
		}
				
		return message;
	}
}

class LevenshteinDistance
{
	private static int minimum(int a, int b, int c)
	{
		return Math.min(Math.min(a, b), c);
	}

	public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2)
	{
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1]
						+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

		return distance[str1.length()][str2.length()];
	}
}