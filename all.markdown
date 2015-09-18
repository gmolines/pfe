---
layout: page
title: all
longtitle: Sujets proposés en 2015-2016
permalink: /all-subjects/
---
{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
  * {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} [{{post.title}}]({{ site.baseurl }}{{ post.url }}) (*{{post.contact}}*)
{% endfor %}