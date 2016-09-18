---
layout: page
title: all
longtitle: Sujets proposés en 2016-2017
permalink: /pids/
---
{% assign sorted_posts = (site.posts | sort: 'pid') %}

{% for post in sorted_posts %}
  * `{{post.pid}}` [{{post.title}}]({{ site.baseurl }}{{ post.url }})
{% endfor %}